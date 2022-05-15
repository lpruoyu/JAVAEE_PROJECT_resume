package programmer.lp.resume.servlet;

import org.apache.commons.beanutils.BeanUtils;
import programmer.lp.resume.base.BaseServlet;
import programmer.lp.resume.bean.Award;
import programmer.lp.resume.service.impl.AwardServiceImpl;
import programmer.lp.resume.util.Uploads;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@WebServlet("/award/*")
public class AwardServlet extends BaseServlet<Award> {

//    private final AwardService service = new AwardServiceImpl();

    public void admin(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setAttribute("awards", service.list());
            forward(req, resp, "admin/award.jsp");
        } catch (Exception e) {
            forwardError(e, req, resp);
        }
    }

    public void save(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Map<String, String> requestParams = Uploads.parseParam(req);
            String finalImage = requestParams.get("finalImage");
            final Award award = new Award();
            BeanUtils.populate(award, requestParams);
            String oldImage = null;
            if (null != finalImage) {
                oldImage = award.getImage();
                award.setImage(finalImage);
            }
            try {
                if (service.save(award)) {
                    // 保存成功，删除旧图片
                    if (null != oldImage && !"".equals(oldImage)) {
                        Uploads.removeImage(oldImage, req.getServletContext());
                    }
                    redirect(req, resp, "award/admin");
                } else {
                    forwardError("获奖信息保存失败", req, resp);
                }
            } catch (Exception e) {
                forwardError(e, req, resp);
                // 保存失败
                if (null != finalImage) {
                    Uploads.removeImage(finalImage, req.getServletContext());
                }
            }
        } catch (Exception e) {
            forwardError(e, req, resp);
        }
    }

    public void remove(HttpServletRequest req, HttpServletResponse resp) {
        ((AwardServiceImpl) service).setLocalFileBasePath(req.getServletContext().getRealPath(""));
        try {
            if (service.removeAll(intIds(req.getParameterValues("id")))) {
                redirect(req, resp, "award/admin");
            } else {
                forwardError("获奖信息删除失败", req, resp);
            }
        } catch (Exception e) {
            forwardError(e, req, resp);
        }
    }

//
//    public void save(HttpServletRequest req, HttpServletResponse resp) {
//        try {
//
//
//            ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
//            upload.setHeaderEncoding("UTF-8");
//            // 一个FileItem就代表一个请求参数（不要受名字影响，FileItem既包含文件参数，也包含非文件参数）
//            final List<FileItem> fileItems = upload.parseRequest(req);
//            for (FileItem item : fileItems) {
////                System.out.print(item.getFieldName() + " "); // 请求参数名
////                System.out.print(item.getName() + " "); // 文件参数的文件名
////                System.out.println(item.getString("UTF-8")); // 非文件参数的值
//
//
//                String fieldName = item.getFieldName();
//                if (item.isFormField()) { // 非文件
//                    System.out.println("fieldName : " + fieldName + " ; " + "parameter : " + item.getString("UTF-8"));
//                } else { // 文件
//                    System.out.println("fieldName : " + fieldName + " ; " + "fileName : " + item.getName());
//                    FileUtils.copyInputStreamToFile(item.getInputStream(), new File("D:/aaa.jpg"));
//
////                    try (
////                            BufferedInputStream bis = new BufferedInputStream(item.getInputStream());
////                            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File("D:/aaa.jpg")))
////                    ) {
////
////                        final byte[] buff = new byte[4096];
////                        int len;
////                        while ((len = bis.read(buff)) != -1) {
////                            bos.write(buff, 0, len);
////                        }
////
////                    }
//
//                }
//
//            }
//
//
////            Award award = new Award();
////            BeanUtils.populate(award, req.getParameterMap());
////            if (service.save(award)) {
////                resp.sendRedirect(req.getContextPath() + "/award/admin");
////            } else {
////                forwardError("获奖信息保存失败", req, resp);
////            }
//
//
//        } catch (Exception e) {
//            forwardError(e, req, resp);
//        }
//    }

}
