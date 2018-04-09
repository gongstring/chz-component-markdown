package com.chz.component.markdown.util;

import com.youbenzi.mdtool.export.HTMLDecorator;
import com.youbenzi.mdtool.markdown.Analyzer;
import com.youbenzi.mdtool.markdown.Block;

import java.io.*;
import java.util.List;

/**
 * 重写MDT工具类，解决乱码问题
 */
public class MDToolExt {

    public static String markdown2Html(File file,String charset) throws IOException{
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset))){
            String lineStr = null;

            StringBuffer sb = new StringBuffer();
            while ((lineStr = reader.readLine())!=null) {
                sb.append(lineStr).append("\n");
            }
            return markdown2Html(sb.toString());
        } catch (IOException e) {
            throw  e;
        }
    }

    public static String markdown2Html(File file) throws IOException{
        return markdown2Html(file,"UTF-8");
    }

    public static String markdown2Html(String mdStr){
        if(mdStr==null){
            return null;
        }

        List<Block> list = Analyzer.analyze(mdStr);
        HTMLDecorator decorator = new HTMLDecorator();

        decorator.decorate(list);
        return decorator.outputHtml();
    }
}
