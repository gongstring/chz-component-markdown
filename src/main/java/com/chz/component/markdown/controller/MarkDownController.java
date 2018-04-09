package com.chz.component.markdown.controller;

import com.chz.component.markdown.bean.DocumentBean;
import com.chz.component.markdown.bean.GroupBean;
import com.chz.component.markdown.bean.MenuBean;
import com.chz.component.markdown.common.ParamComponent;
import com.chz.component.markdown.util.LocalCacheUtil;
import com.chz.component.markdown.util.MDToolExt;
import com.youbenzi.mdtool.tool.MDTool;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MarkDownController {

    @Autowired
    private ParamComponent paramComponent;

    @GetMapping({"/index.html","/","/index"})
    public void home(HttpServletResponse response) throws IOException {
        response.sendRedirect("/wiki/dev/index.html");
    }

    @GetMapping({"/image/{profile}/{filename}.{ext}"})
    public void image(@PathVariable String profile,@PathVariable String filename,@PathVariable String ext,HttpServletResponse response){
        rendImage(response,profile,filename+"."+ext);
    }

    @GetMapping("clear")
    @ResponseBody
    public String clear(){
        LocalCacheUtil.removeAll();
        return "清空缓存成功！<a style='margin-left:20px;' href='/'>返回</a>";
    }

    @GetMapping("/wiki/{profile}/index.html")
    public ModelAndView index(@PathVariable String profile, HttpServletRequest request){
        ModelAndView model = new ModelAndView();
        model.setViewName("index");
        DocumentBean documentBean = readWikis(profile);
        model.addObject("groups",documentBean.getGroupBeans());
        model.addObject("docName",documentBean.getName());
        model.addObject("profile",profile);
        return model;
    }

    @GetMapping("markdown/{profile}/{id}")
    public void markdown(@PathVariable String profile,@PathVariable String id, HttpServletResponse response){
        response.setContentType("text/html;charset=utf-8");
        try(PrintWriter out = response.getWriter()) {
            DocumentBean documentBean = readWikis(profile);
            if(documentBean == null || paramComponent.getDocDir() == null || "".equals(paramComponent.getDocDir()))
                out.append("没有找到您要查看的文档！");
            else{
                MenuBean menu = findMenuByPath(documentBean.getGroupBeans(),id);
                if(menu != null){
                    String filePath = "";
                    if(menu.getAbsolute())
                        filePath = menu.getPath();
                    else
                        filePath = paramComponent.getDocDir() + menu.getPath();
                    try {
                        String content = MDToolExt.markdown2Html(new File(filePath));
                        out.append(content);
                    }catch (Exception e){
                        out.append("没有找到您要查看的文档！");
                    }
                }else
                    out.append("没有找到您要查看的文档！");
            }
            out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 读取wiki文档
     * @param profile
     * @return
     */
    private synchronized DocumentBean readWikis(String profile){
        String key = "wiki_"+profile;
        if(LocalCacheUtil.get(key) != null)
            return (DocumentBean)LocalCacheUtil.get(key);

        DocumentBean bean = readMenuXml("properties/wiki_"+profile+".xml");
        LocalCacheUtil.set(key,bean);
        return bean;
    }

    /**
     * 读取菜单XML数据
     * @param xmlFilePath
     * @return
     */
    private synchronized DocumentBean readMenuXml(String xmlFilePath){
        if(paramComponent.getDocDir() == null || "".equals(paramComponent.getDocDir()))
            return null;


        DocumentBean bean = new DocumentBean();
        SAXBuilder sbBuilder = new SAXBuilder();
        Document doc = null;
        try {
            doc = sbBuilder.build(new FileInputStream(paramComponent.getDocDir()+"/"+xmlFilePath));
            //读取根元素
            Element rootEmt = doc.getRootElement();
            String rootPath = rootEmt.getAttributeValue("rootPath");
            bean.setName(rootEmt.getAttributeValue("name"));

            //读取分组
            List<GroupBean> groupBeans = new ArrayList<>();
            List<Element> groups = rootEmt.getChildren("group");
            for (Element group : groups) {
                GroupBean groupBean = new GroupBean();
                groupBean.setName(group.getAttributeValue("name"));
                List<Element> menuEmts = group.getChildren();
                if(menuEmts == null)
                    continue;

                groupBean.setMenus(findMenuTree(menuEmts));
                groupBeans.add(groupBean);
            }

            bean.setGroupBeans(groupBeans);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    /**
     * 获取菜单树
     * @param menuEmts
     * @return
     */
    private synchronized List<MenuBean> findMenuTree(List<Element> menuEmts){
        List<MenuBean> menus = new ArrayList<>();
        for (Element menuEmt:menuEmts) {
            MenuBean menu = new MenuBean();
            menu.setId(menuEmt.getAttributeValue("id"));
            menu.setName(menuEmt.getAttributeValue("name"));
            menu.setAbsolute(menuEmt.getAttributeValue("absolute") != null ? Boolean.getBoolean(menuEmt.getAttributeValue("absolute")) : false );
            menu.setPath(menuEmt.getAttributeValue("path"));

            List<Element> menuChildren = menuEmt.getChildren();
            if(menuChildren == null)
                continue;

            menu.setChildren(findMenuTree(menuChildren));
            menus.add(menu);
        }
        return menus;
    }

    /**
     * 根据文档路径，找到菜单定义
     * @param groupBeans
     * @param id
     * @return
     */
    private synchronized MenuBean findMenuByPath(List<GroupBean> groupBeans,String id){
        if(groupBeans == null)
            return null;

        for (GroupBean group:groupBeans) {
            if(group.getMenus() == null)
                continue;

            MenuBean menu = findMenuByTree(group.getMenus(),id);
            if(menu != null)
                return menu;
        }
        return null;
    }

    private synchronized MenuBean findMenuByTree(List<MenuBean> menuBeans,String id){
        for (MenuBean menu:menuBeans) {
            if(id.equals(menu.getId()))
                return menu;

            if(menu.getChildren() != null && menu.getChildren().size() > 0)
                return findMenuByTree(menu.getChildren(),id);
        }
        return null;
    }

    private synchronized void rendImage(HttpServletResponse response,String profile,String fileName){
        DocumentBean documentBean = readWikis(profile);
        if(documentBean == null || paramComponent.getDocDir() == null || "".equals(paramComponent.getDocDir()))
            return;
        String imageFile = paramComponent.getDocDir()+"/image/"+fileName;
        File file = new File(imageFile);
        if(!file.exists())
            return;

        //使用此方法，不用手动在finally中关闭流
        try(OutputStream os = response.getOutputStream();FileInputStream fis = new FileInputStream(file)) {
            long size = file.length();
            byte[] temp = new byte[(int) size];
            fis.read(temp, 0, (int) size);
            fis.close();
            byte[] data = temp;
            response.setContentType("image/png");
            os.write(data);
            os.flush();
            os.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
