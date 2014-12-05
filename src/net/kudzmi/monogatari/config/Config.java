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

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * @author Dmitry Kuznetsov aka 「kudzmi」
 */
public class Config {
    private static final String CFG_DIR = "config" + File.separator;
    private static final String GLOBAL_CFG = CFG_DIR + "global.cfg";
    private static final String INDEX_CFG = CFG_DIR + "index.cfg";
    private static Config instance;
    private Global global;
    private Index index;

    private Config() {
    }

    public static Config it() {
        if (instance == null)
            instance = new Config();
        return instance;
    }

    public void load() throws Exception {
        Gson gson = new Gson();
        try (BufferedReader reader = new BufferedReader(new FileReader(GLOBAL_CFG))) {
            this.global = gson.fromJson(reader, Global.class);
        } catch (Exception e) {
            throw new Exception("Cannot load GLOBAL config :( \n  " + e.getMessage());
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(INDEX_CFG))) {
            this.index = gson.fromJson(reader, Index.class);
        } catch (Exception e) {
            throw new Exception("Cannot load INDEX config :( \n  " + e.getMessage());
        }
    }

    public Global getGlobal() {
        return global;
    }

    public Index getIndex() {
        return index;
    }
}
