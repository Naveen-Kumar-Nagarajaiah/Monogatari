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

package net.kudzmi.monogatari.config;

import com.google.gson.annotations.SerializedName;

/**
 * @author Dmitry Kuznetsov aka 「kudzmi」
 */
public final class Global {
    @SerializedName("site_name")
    private String siteName;
    @SerializedName("copyright")
    private String copyright;
    @SerializedName("template")
    private String theme;
    @SerializedName("favicon")
    private String favicon;
    @SerializedName("image_width")
    private Integer imageWidth;
    @SerializedName("release_dir_path")
    private String releaseDirPath;
    @SerializedName("raw_data_path")
    private String rawDataPath;

    public String getSiteName() {
        return siteName;
    }

    public String getCopyright() {
        return copyright.trim();
    }

    public String getThemeName() {
        return theme.trim();
    }

    public String getFavicon() {
        return favicon.trim();
    }

    public Integer getImageWidth() {
        return imageWidth;
    }

    public String getReleaseDirPath() {
        return releaseDirPath;
    }

    public String getRawDataPath() {
        return rawDataPath;
    }

}
