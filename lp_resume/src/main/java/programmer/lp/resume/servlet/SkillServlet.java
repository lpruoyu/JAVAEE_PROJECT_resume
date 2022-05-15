package programmer.lp.resume.servlet;

import org.apache.commons.beanutils.BeanUtils;
import programmer.lp.resume.base.BaseServlet;
import programmer.lp.resume.bean.Skill;
import programmer.lp.resume.service.SkillService;
import programmer.lp.resume.service.impl.SkillServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/skill/*")
public class SkillServlet extends BaseServlet<Skill> {

    private final SkillService service = new SkillServiceImpl();

    public void admin(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setAttribute("skills", service.list());
            forward(req, resp, "admin/skill.jsp");
        } catch (Exception e) {
            forwardError(e, req, resp);
        }
    }

    public void save(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Skill skill = new Skill();
            BeanUtils.populate(skill, req.getParameterMap());
            if (service.save(skill)) {
                redirect(req, resp, "skill/admin");
            } else {
                forwardError("技能信息保存失败", req, resp);
            }
        } catch (Exception e) {
            forwardError(e, req, resp);
        }
    }

    public void remove(HttpServletRequest req, HttpServletResponse resp) {
        try {
            if (service.removeAll(intIds(req.getParameterValues("id")))) {
                redirect(req, resp, "skill/admin");
            } else {
                forwardError("技能信息删除失败", req, resp);
            }
        } catch (Exception e) {
            forwardError(e, req, resp);
        }
    }

}
