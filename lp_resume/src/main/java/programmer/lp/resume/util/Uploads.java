package programmer.lp.resume.util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class Uploads {

    private static final String BASE_DIR = "upload";
    private static final String IMAGE_DIR = "image";

    public static String uploadImage(HttpServletRequest req, FileItem item) throws Exception {
        // 是文件参数 用户也不一定上传了文件
        // 未上传文件的话，item.getName()为空字符串
        // 表单中的属性用户不填时返回值都为空字符串
//        final String paramFileName = item.getName();
//        if (null == paramFileName || "".equals(paramFileName)) {
//            return null;   // 用户未选择文件，就不应该执行保存操作
//        }
        // 也可以这样判断：
        if (item.getInputStream().available() <= 0) {
            return null;
        }
        final String localRelativeDirStr = BASE_DIR + "/" + IMAGE_DIR + "/";
        final String localFileDir = req.getServletContext().getRealPath(localRelativeDirStr);
        final String localFileName = UUID.randomUUID() + "." + FilenameUtils.getExtension(item.getName());
        FileUtils.copyInputStreamToFile(item.getInputStream(), new File(localFileDir, localFileName));
        return localRelativeDirStr + localFileName;
    }

    public static void removeImage(String name, String localFileBasePath) {
        try {
            FileUtils.forceDelete(new File(localFileBasePath, name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeImage(String name, ServletContext context) {
        final String localFileDir = context.getRealPath("/");
        try {
            FileUtils.forceDelete(new File(localFileDir, name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> parseParam(HttpServletRequest req) throws Exception {
        final ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
        upload.setHeaderEncoding("UTF-8");
        final List<FileItem> items = upload.parseRequest(req);
        final Map<String, String> requestParams = new HashMap<>();
        for (FileItem fileItem : items) {
            final String fieldName = fileItem.getFieldName();
            if (fileItem.isFormField()) { // 非文件
                requestParams.put(fieldName, fileItem.getString("UTF-8"));
            } else { // 文件
                // 这里只会有一个图片文件
                // TODO 这里也可以使用fieldName，之后有时间修改
                requestParams.put("finalImage", Uploads.uploadImage(req, fileItem));
            }
        }
        return requestParams;
    }

}
