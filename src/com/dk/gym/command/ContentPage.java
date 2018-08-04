package com.dk.gym.command;

public class ContentPage {
    private RequestMethod requestMethod;
    private String pageURL;

    public ContentPage() {
    }

    public ContentPage(RequestMethod requestMethod, String pageURL) {
        this.requestMethod = requestMethod;
        this.pageURL = pageURL;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getPageURL() {
        return pageURL;
    }

    public void setPageURL(String pageURL) {
        this.pageURL = pageURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContentPage that = (ContentPage) o;

        if (requestMethod != that.requestMethod) return false;
        return pageURL != null ? pageURL.equals(that.pageURL) : that.pageURL == null;
    }

    @Override
    public int hashCode() {
        int result = requestMethod != null ? requestMethod.hashCode() : 0;
        result = 31 * result + (pageURL != null ? pageURL.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ContentPage{" +
                "requestMethod=" + requestMethod +
                ", pageURL='" + pageURL + '\'' +
                '}';
    }
}
