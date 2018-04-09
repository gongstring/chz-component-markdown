package com.chz.component.markdown.test;

import org.junit.Test;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class MarkDownTest {

    @Test
    public void demoTest(){
        Parser parser = Parser.builder().build();
        Node document = parser.parse("This is *Sparta*");
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        System.out.println(renderer.render(document));  // "<p>This is <em>Sparta</em></p>\n"

    }

}
