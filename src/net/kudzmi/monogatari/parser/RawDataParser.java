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

import net.kudzmi.monogatari.Markers;
import net.kudzmi.monogatari.PTH;
import net.kudzmi.monogatari.Tools;
import net.kudzmi.monogatari.config.Config;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Dmitry Kuznetsov aka 「kudzmi」
 */
public final class RawDataParser {

    private TreeMap<Integer, String> postsDirsMap = new TreeMap<>();

    public List<PostObject> getRawData() throws IOException {
        final List<PostObject> postsObjects = new ArrayList<>();

        Files.walkFileTree(PTH.RAW_ROOT, new RootVisitor());
        for (Map.Entry<Integer, String> entry : postsDirsMap.entrySet()) {
            final Path filePath = Paths.get(entry.getValue(), PTH.RAW_FILE_NAME);
            final PostObject postObject = parseContentFile(filePath.toFile());
            if (postObject != null) {
                postObject.setNumber(entry.getKey());
                postsObjects.add(postObject);
            }
        }
        return postsObjects;
    }

    private PostObject parseContentFile(File rawFile) {
        final String content = Tools.readFile(rawFile);
        PostObject post = new PostObject();
        String markerBody;
        // Language
        markerBody = getMarkerBody(content, Markers.PAGE_LANG);
        if (markerBody == null) return null;
        post.setLanguage(markerBody);
        //Page title
        markerBody = getMarkerBody(content, Markers.PAGE_TITLE);
        if (markerBody == null) return null;
        post.setPageTitle(markerBody);
        // Post title
        markerBody = getMarkerBody(content, Markers.POST_TITLE);
        if (markerBody == null) return null;
        post.setPostTitle(markerBody);
        // Post date
        markerBody = getMarkerBody(content, Markers.POST_DATE);
        if (markerBody == null) return null;
        post.setPostDate(markerBody);
        //Post text
        markerBody = getMarkerBody(content, Markers.ARTICLE);
        if (markerBody == null) return null;
        post.setArticle(parseArticle(markerBody));

        post.setRawDataDir(rawFile.getParent());
        return post;
    }

    private String getMarkerBody(String src, String marker) {
        int pos1 = src.indexOf(marker);
        int pos2 = src.indexOf(marker, pos1 + marker.length());
        if (pos1 < 0 || pos2 < 0) return null;
        return src.substring(pos1 + marker.length(), pos2);
    }

    private String parseArticle(String text) {
        String marker = Markers.IMAGES;
        int markerSize = marker.length();
        int pos1 = text.indexOf(marker);
        int pos2 = text.indexOf(marker, pos1 + markerSize);
        if (pos1 < 0 || pos2 < 0) return text;
        String imageStr = text.substring(pos1 + markerSize, pos2);
        String[] parts = imageStr.split("#");
        imageStr = "";
        if (parts.length > 0) {
            final StringBuilder builder = new StringBuilder();
            builder.append("<div class=\"images\">");
            for (String part : parts) {
                builder.append("<img src=\"").append(part).append("\" width=\"").append(Config.it().getGlobal().getImageWidth()).append("\" />");
            }
            builder.append("</div>");
            imageStr = builder.toString();
        }

        String result = text.substring(0, pos1) + imageStr + text.substring(pos2 + markerSize);
        if (result.indexOf(marker) > 0) {
            return parseArticle(result);
        }
        return result;
    }

    private final class RootVisitor extends SimpleFileVisitor<Path> {
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            if (dir.getFileName().toString().startsWith(PTH.RAW_POST_PREFIX)) {
                //System.out.println("Found post dir: " + dir.toString()); //for debug
                int pos = dir.toString().lastIndexOf("_");
                if (pos < 0) {
                    System.err.println("Malformed post directory name: " + dir.toString());
                    throw new IOException("Malformed post directory name: " + dir.toString());
                }
                Integer number = Integer.parseInt(dir.toString().substring(pos + 1));
                postsDirsMap.put(number, dir.toString());
            }
            return FileVisitResult.CONTINUE;
        }
    }
}

