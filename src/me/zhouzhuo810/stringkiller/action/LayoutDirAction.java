package me.zhouzhuo810.stringkiller.action;

import com.google.common.collect.Lists;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.IdeActions;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import me.zhouzhuo810.stringkiller.bean.StringEntity;
import me.zhouzhuo810.stringkiller.utils.FileUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * layout文件夹转成strings
 * Created by zz on 2017/9/20.
 */
public class LayoutDirAction extends AnAction {

    private int index = 0;

    @Override
    public void actionPerformed(AnActionEvent e) {

        VirtualFile file = e.getData(PlatformDataKeys.VIRTUAL_FILE);
        if (file == null) {
            showError("找不到目标文件");
            return;
        }

        if (!file.isDirectory()) {
            showError("请选择layout文件夹");
            return;
        } else if (!file.getName().startsWith("layout")) {
            showError("请选择layout文件夹");
            return;
        }

        VirtualFile[] children = file.getChildren();

        StringBuilder sb = new StringBuilder();

        for (VirtualFile child : children) {
            layoutChild(child, sb);
        }

        VirtualFile resDir = file.getParent();
        if (resDir.getName().equalsIgnoreCase("res")) {
            VirtualFile[] chids = resDir.getChildren();
            for (VirtualFile chid : chids) {
                if (chid.getName().startsWith("values")) {
                    if (chid.isDirectory()) {
                        VirtualFile[] values = chid.getChildren();
                        for (VirtualFile value : values) {
                            if (value.getName().startsWith("strings")) {
                                try {
                                    String content = new String(value.contentsToByteArray(), "utf-8");
                                    System.out.println("utf-8=" + content);
                                    String result = content.replace("</resources>", sb.toString() + "\n</resources>");
                                    FileUtils.replaceContentToFile(value.getPath(), result);
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }

        e.getActionManager().getAction(IdeActions.ACTION_SYNCHRONIZE).actionPerformed(e);

    }

    private void layoutChild(VirtualFile file, StringBuilder sb) {
        index = 0;

        String extension = file.getExtension();
        if (extension != null && extension.equalsIgnoreCase("xml")) {
            if (!file.getParent().getName().startsWith("layout")) {
                showError("请选择布局文件");
                return;
            }
        }

//        showHint(file.getName());
        List<StringEntity> strings;
        StringBuilder oldContent = new StringBuilder();
        try {
            oldContent.append(new String(file.contentsToByteArray(), "utf-8"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        InputStream is = null;
        try {
            is = file.getInputStream();
            strings = extraStringEntity(is, file.getNameWithoutExtension().toLowerCase(), oldContent);
            if (strings != null) {
                for (StringEntity string : strings) {
                    sb.append("\n    <string name=\"" + string.getId() + "\">" + string.getValue() + "</string>");
                }
                FileUtils.replaceContentToFile(file.getPath(), oldContent.toString());
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            FileUtils.closeQuietly(is);
        }

    }

    private List<StringEntity> extraStringEntity(InputStream is, String fileName, StringBuilder oldContent) {
        List<StringEntity> strings = Lists.newArrayList();
        try {
            return generateStrings(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is), strings, fileName, oldContent);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    private List<StringEntity> generateStrings(Node node, List<StringEntity> strings, String fileName, StringBuilder oldContent) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Node stringNode = node.getAttributes().getNamedItem("android:text");
            if (stringNode != null) {
                String value = stringNode.getNodeValue();
                if (!value.contains("@string")) {
                    final String id = fileName + "_text_" + (index++);
                    strings.add(new StringEntity(id, value));
                    String newContent = oldContent.toString().replaceFirst("\"" + value + "\"", "\"@string/" + id + "\"");
                    oldContent = oldContent.replace(0, oldContent.length(), newContent);
                }
            }
        }
        NodeList children = node.getChildNodes();
        for (int j = 0; j < children.getLength(); j++) {
            generateStrings(children.item(j), strings, fileName, oldContent);
        }
        return strings;
    }


    private void showHint(String msg) {
        Notifications.Bus.notify(new Notification("StringKiller", "StringKiller", msg, NotificationType.WARNING));
    }

    private void showError(String msg) {
        Notifications.Bus.notify(new Notification("StringKiller", "StringKiller", msg, NotificationType.ERROR));
    }
}
