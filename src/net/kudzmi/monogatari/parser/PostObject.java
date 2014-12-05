/*
 This file is part of Monogatari.

 Monogatari is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Monogatari is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Monogatari.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.kudzmi.monogatari.parser;

/**
 * @author Dmitry Kuznetsov aka 「kudzmi」
 */
public class PostObject {
    private int number;
    private String language;
    private String rawDataDir;
    private String pageTitle;
    private String postTitle;
    private String postDate;
    private String article;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getRawDataDir() {
        return rawDataDir;
    }

    public void setRawDataDir(String rawDataDir) {
        this.rawDataDir = rawDataDir;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostTitleF() {
        return postTitle.replaceAll(" ", "_");
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getArticle() {
        return article.trim().replaceAll("\\n", "<br />");
    }

    public void setArticle(String article) {
        this.article = article;
    }
}
