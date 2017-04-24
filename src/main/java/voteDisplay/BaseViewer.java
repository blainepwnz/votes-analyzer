package voteDisplay;

/**
 * Created by Andrew on 24.04.2017.
 */
public abstract class BaseViewer {

    protected void addNextLine(StringBuilder sb) {
        sb.append("<br />");
    }

    protected void makeItalic(StringBuilder sb, Object obj) {
        sb.append("<i>")
            .append(obj)
            .append("</i>");
    }

    protected void makeBold(StringBuilder sb, Object obj) {
        sb.append("<b>")
            .append(obj)
            .append("</b>");
    }
}
