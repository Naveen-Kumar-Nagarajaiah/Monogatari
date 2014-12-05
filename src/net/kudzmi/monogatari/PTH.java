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
package net.kudzmi.monogatari;

import net.kudzmi.monogatari.config.Config;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Dmitry Kuznetsov aka 「kudzmi」
 */
public class PTH {


    /* source */
    public static final String RAW_EXT = ".txt";
    public static final String RAW_FILE_NAME = "content".concat(RAW_EXT);
    public static final Path RAW_ROOT = Paths.get(Config.it().getGlobal().getRawDataPath());
    public static final String RAW_POST_PREFIX = "post_";


    /* template */
    public static final String TMP_EXT = ".html";
    /* release */
    public static final Path RELEASE_ROOT = Paths.get(Config.it().getGlobal().getReleaseDirPath());
    public static final Path RELEASE_INDEX = RELEASE_ROOT.resolve("index");
    public static final Path RELEASE_POSTS = RELEASE_ROOT.resolve("posts");
    public static final String RELEASE_POST_PREFIX = "post_";
    /* styles */
    public static final String GLOBAL_STYLE_FILE = "global.css";
    private static final String TMP_ROOT = "templates";

    public static Path getPostTemplate(String tmpName) {
        return Paths.get(TMP_ROOT, tmpName, "post", "post_template".concat(TMP_EXT));
    }

    public static Path getIndexTemplate(String tmpName) {
        return Paths.get(TMP_ROOT, tmpName, "index", "index_template".concat(TMP_EXT));
    }

    public static Path getThemeRoot(String tmpName) {
        return Paths.get(TMP_ROOT, tmpName);
    }
}
