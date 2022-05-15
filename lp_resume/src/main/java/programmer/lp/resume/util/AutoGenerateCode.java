package programmer.lp.resume.util;

import org.apache.commons.io.FileUtils;
import programmer.lp.resume.bean.Contact;
import programmer.lp.resume.dao.impl.ContactDaoImpl;

import java.io.File;
import java.util.*;

public class AutoGenerateCode {

    private static final String baseDir = "D:\\code_space\\LearnJava\\IntellijIdea\\LearnJavaEEWithXMGLMJ\\StepTWO\\lp_resume\\src\\main\\java\\programmer\\lp\\resume\\";
    private static final String replacement = "#name#";

    private static final Class[] classes = {
    };

    private static final Map<String, String> maps = new HashMap<>();

    static {
        maps.put("Dao", "dao");
        maps.put("DaoImpl", "dao/impl");
        maps.put("Service", "service");
        maps.put("ServiceImpl", "service/impl");
        maps.put("Servlet", "servlet");
    }

    public static void generateTPL() {
        try {
            for (Class aClass : classes) {
                final Set<Map.Entry<String, String>> entries = maps.entrySet();
                for (Map.Entry<String, String> entry : entries) {
                    final String key = entry.getKey();
                    final String value = entry.getValue();
                    final String classSimpleName = aClass.getSimpleName();
                    final String tplPath = AutoGenerateCode.class.getClassLoader().getResource("tpl/" + key + ".tpl").getFile();
                    final String tplSourceStr = FileUtils.readFileToString(new File(tplPath), "UTF-8");
                    final String tplNewStr = tplSourceStr.replace(replacement, classSimpleName);
                    final String outFileName = classSimpleName + key + ".java";
                    final String outFileDir = baseDir + value;
                    final File finalFile = new File(outFileDir, outFileName);
                    if (!finalFile.exists()) {
                        FileUtils.write(finalFile, tplNewStr);
                    } else {
                        System.out.println(finalFile.getName() + "文件已经存在");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateContactToDB() {
        ContactDaoImpl contactDaoImpl = new ContactDaoImpl();
        Random random = new Random();
        for (int i = 0; i < 200; i++) {
            Contact contact = new Contact();
            contact.setName("name" + random.nextInt(1000));
            contact.setEmail(random.nextInt(10000) + "lp@qq.com");
            contact.setComment(random.nextInt(10000) + "comment" + random.nextInt(10000));
            contact.setSubject(random.nextInt(10000) + "subject" + random.nextInt(10000));
            Date date = new Date();
            date.setYear(random.nextInt(100) + 100); // 从（1900 + 100 = 2000）年开始 加上1-100的随机数
            date.setMonth(1 + random.nextInt(12));
            date.setDate(1 + random.nextInt(28));
            contact.setCreatedTime(date);
            contactDaoImpl.save(contact);
        }
    }

}