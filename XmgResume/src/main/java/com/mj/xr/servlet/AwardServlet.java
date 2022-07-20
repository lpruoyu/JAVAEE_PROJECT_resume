package com.mj.xr.servlet;

import com.mj.xr.bean.Award;
import com.mj.xr.bean.UploadParams;
import com.mj.xr.util.Uploads;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@WebServlet("/award/*")
public class AwardServlet extends BaseServlet<Award> {

    public void admin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("awards", service.list());
        forward(request, response, "admin/award.jsp");


    }

    public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
        UploadParams uploadParams = Uploads.parseRequest(request);

        Award award = new Award();
        BeanUtils.populate(award, uploadParams.getParams());

        FileItem item = uploadParams.getFileParam("imageFile");
        award.setImage(Uploads.uploadImage(item, request, award.getImage()));

        if (service.save(award)) { // 保存成功
            redirect(request, response, "award/admin");
        } else {
            forwardError(request, response, "获奖成就保存失败");
        }
    }

    public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] idStrs = request.getParameterValues("id");
        List<Integer> ids = new ArrayList<>();
        for (String idStr : idStrs) {
            ids.add(Integer.valueOf(idStr));
        }
        // 删除
        if (service.remove(ids)) {
            redirect(request, response, "award/admin");
        } else {
            forwardError(request, response, "获奖成就删除失败");
        }
    }
}
