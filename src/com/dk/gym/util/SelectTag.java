package com.dk.gym.util;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

@SuppressWarnings("serial")
public class SelectTag extends TagSupport {

    private String tagId;
    private String elem;

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public void setElem(String elem) {
        this.elem = elem;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            String part1 = String.format("%s%s%s", "<c:out value=\"", elem, "\"/>");
            String part2 = String.format("%s%s%s",
                    "<script>document.getElementById(\"",
                    tagId,
                    "\").style.backgroundColor = \"#ebe6d3\"</script>");

            if (elem == null || elem.isEmpty()) {
                pageContext.getOut().write(part1 + part2);
            }
        } catch (IOException e) {

            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }


}
