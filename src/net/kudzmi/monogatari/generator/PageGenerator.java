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
package net.kudzmi.monogatari.generator;

import net.kudzmi.monogatari.Markers;
import net.kudzmi.monogatari.PTH;
import net.kudzmi.monogatari.Tools;
import net.kudzmi.monogatari.config.Config;
import net.kudzmi.monogatari.parser.PostObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Dmitry Kuznetsov aka 「kudzmi」
 */
public final class PageGenerator {

    public static IndexPostObject generatePost(PostObject po) throws IOException {
        final String themeName = Config.it().getGlobal().getThemeName();
        final IndexPostObject ipo = new IndexPostObject();
        ipo.setDate(po.getPostDate());
        ipo.setTitle(po.getPostTitle());
        ipo.setText(po.getArticle());
        String template = Tools.readFile(PTH.getPostTemplate(themeName).toFile());
        template = template.replace(Markers.PAGE_LANG, po.getLanguage());
        template = template.replace(Markers.PAGE_TITLE, po.getPageTitle());
        template = template.replace(Markers.POST_TITLE, po.getPostTitle());
        template = template.replace(Markers.POST_DATE, po.getPostDate());
        template = template.replace(Markers.ARTICLE, po.getArticle());
        template = template.replace(Markers.PAGE_COPYRIGHT, Config.it().getGlobal().getCopyright());

        final Path postFilePath = PTH.RELEASE_POSTS.resolve(PTH.RELEASE_POST_PREFIX + po.getNumber()).resolve("index.html");
        ipo.setUrl(postFilePath.toString());
        ipo.setNumber(po.getNumber());
        final File postFile = postFilePath.toFile();
        if (!postFile.exists()) {
            if (!postFile.getParentFile().mkdirs())
                System.out.println("INFO: no need to make path " + postFile.getAbsolutePath());
        }
        Tools.writeFile(postFile, template);
        final Path path = Paths.get(po.getRawDataDir());
        copyPostStuff(path, postFilePath);
        return ipo;
    }

    private static void copyPostStuff(Path path, Path postFilePath) throws IOException {
        // copy from raw template folder
        final String theme = Config.it().getGlobal().getThemeName();
        Files.list(PTH.getPostTemplate(theme).getParent()).filter(new FileFilter(PTH.TMP_EXT)).forEach(f -> {
            final Path destination = postFilePath.resolveSibling(f.getFileName());
            try {
                Files.copy(f, destination, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // copy from raw data
        Files.list(path).filter(new FileFilter(PTH.RAW_EXT)).forEach(f -> {
            final Path destination = postFilePath.resolveSibling(f.getFileName());
            try {
                Files.copy(f, destination, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }

    public static void generateIndex(List<IndexPostObject> objects) throws IOException {
        float recPerPage = Config.it().getIndex().getPostsPerPage();
        int size = objects.size();
        final int pages = (int) Math.ceil(size / recPerPage);

        String tableContent = "";
        int currentPage = 0;
        for (int i = 1; i <= size; i++) {
            final IndexPostObject ipo = objects.get(size - i);
            tableContent += getOneRecord(ipo.getNumber(), ipo.getTitle(), ipo.getText(), ipo.getDate());
            if (i % recPerPage == 0 || i == size) {
                saveIndexFile(tableContent, currentPage++, pages);
                tableContent = "";
            }
        }
        copyIndexStuff();
    }

    private static String getOneRecord(int postNum, String postTitle, String postText, String postDate) {
        String record = "<div class=\"record\">\n" +
                "\t\t<div class=\"image\">\n" +
                "\t\t\t<img src=\"posts/post_{post_num}/preview.jpg\" width=\"140\" height=\"140\" />\n" +
                "\t\t</div>\n" +
                "\t\t<div class=\"text\">\n" +
                "\t\t\t<a href=\"posts/post_{post_num}/index.html\">{post_title}</a><br />\n" +
                "\t\t\t{post_date}<br />\n" +
                "\t\t\t<p>{post_text}</p>\n" +
                "\t\t</div>\n" +
                "\t</div>";
        return record.replace("{post_num}", String.valueOf(postNum))
                .replace("{post_num}", String.valueOf(postNum))
                .replace("{post_title}", postTitle)
                .replace("{post_text}", postText)
                .replace("{post_date}", postDate);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void copyIndexStuff() throws IOException {
        final String themeName = Config.it().getGlobal().getThemeName();
        final Path indexRoot = PTH.getIndexTemplate(themeName).getParent();
        Files.list(indexRoot).filter(new FileFilter(PTH.TMP_EXT)).forEach(f -> {
            PTH.RELEASE_INDEX.toFile().mkdirs();
            final Path destination = PTH.RELEASE_INDEX.resolve(f.getFileName());

            try {
                Files.copy(f, destination, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static void saveIndexFile(String tableContent, int page, int total) {
        final String themeName = Config.it().getGlobal().getThemeName();
        String template = Tools.readFile(PTH.getIndexTemplate(themeName).toFile());
        String fName = page == 0 ? "index.html" : "index_" + page + ".html";
        String pageNav = "";
        if (total > 1) {
            if (page == 0) {
                pageNav = "&nbsp;&nbsp;<b>" + page + "</b>&nbsp;<a href=\"index_" + (page + 1) + ".html\">&#8594;</a>";
            }
            if (page > 0 && page < total - 1) {
                if (page > 1)
                    pageNav = "<a href=\"index_" + (page - 1) + ".html\">&#8592;</a>&nbsp;<b>" + page + "</b>&nbsp;<a href=\"index_" + (page + 1) + ".html\">&#8594;</a>";
                else
                    pageNav = "<a href=\"index.html\">&#8592;</a>&nbsp;<b>" + page + "</b>&nbsp;<a href=\"index_" + (page + 1) + ".html\">&#8594;</a>";
            }

            if (page == total - 1) {
                if (page == 1)
                    pageNav = "<a href=\"index.html\">&#8592;</a>&nbsp;<b>" + page + "</b>&nbsp;&nbsp;";
                else
                    pageNav = "<a href=\"index_" + (page - 1) + ".html\">&#8592;</a>&nbsp;<b>" + page + "</b>&nbsp;&nbsp;";
            }
        }
        template = template.replace(Markers.SITE_NAME, Config.it().getGlobal().getSiteName());
        template = template.replace(Markers.INDEX_TITLE, Config.it().getIndex().getTitle());
        template = template.replace(Markers.PAGE_NAVIGATION, pageNav);
        template = template.replace(Markers.INDEX_TABLE, tableContent);
        Tools.writeFile(PTH.RELEASE_ROOT.resolve(fName).toFile(), template);
    }

    public static void copyGlobalFiles() {
        Path src, dst;
        final String theme = Config.it().getGlobal().getThemeName();
        final String favicon = Config.it().getGlobal().getFavicon();
        try {
            // global.css
            src = PTH.getThemeRoot(theme).resolve(PTH.GLOBAL_STYLE_FILE);
            dst = PTH.RELEASE_ROOT.resolve(PTH.GLOBAL_STYLE_FILE);
            Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING);
            // favicon
            if (favicon != null) {
                src = PTH.getThemeRoot(theme).resolve(favicon);
                dst = PTH.RELEASE_ROOT.resolve(favicon);
                Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class FileFilter implements Predicate<Path> {
        private String suffix;

        FileFilter(String suffix) {
            this.suffix = suffix;
        }

        @Override
        public boolean test(Path path) {
            return !path.toString().endsWith(suffix);
        }
    }

}
