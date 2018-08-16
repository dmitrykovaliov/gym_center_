package com.dk.gym.util;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * The Class SelectTag. Selecting empty table cells in jsp pages.
 */
@SuppressWarnings("serial")
public class SelectTag extends TagSupport {

    /** The tag id. */
    private String tagId;
    
    /** The elem. */
    private String elem;

    /**
     * Sets the tag id.
     *
     * @param tagId the new tag id
     */
    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    /**
     * Sets the elem.
     *
     * @param elem the new elem
     */
    public void setElem(String elem) {
        this.elem = elem;
    }

    /**
     * Do start tag.
     *
     * @return the int
     * @throws JspException the jsp exception
     */
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
