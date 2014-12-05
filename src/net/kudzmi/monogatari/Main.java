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
import net.kudzmi.monogatari.generator.IndexPostObject;
import net.kudzmi.monogatari.generator.PageGenerator;
import net.kudzmi.monogatari.parser.PostObject;
import net.kudzmi.monogatari.parser.RawDataParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitry Kuznetsov aka 「kudzmi」
 */
public class Main {

    public static void main(String[] args) {
        checkArgs(args);
        out("Tut-tu-ru~! (^-^)/ Monogatari Static Site Generator desu!");
        out("Loading config...");
        try {
            Config.it().load();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        out("Config for '" + Config.it().getGlobal().getSiteName() + "' successfully loaded.");
        out("Parsing raw data...");
        List<PostObject> posts;
        try {
            posts = new RawDataParser().getRawData();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        out("Generating posts...");
        final List<IndexPostObject> ipoList = new ArrayList<>(posts.size());
        for (PostObject post : posts) {
            try {
                ipoList.add(PageGenerator.generatePost(post));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        out("Generating index...");
        try {
            PageGenerator.generateIndex(ipoList);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        out("Copying global files...");
        PageGenerator.copyGlobalFiles();
        out("\nYatta!");
        out("You site has been generated! You can find it in '" + Config.it().getGlobal().getReleaseDirPath() + "' dir.");

    }

    private static void checkArgs(String[] args) {
        if (args.length == 0) return;
        if (args[0].equals("--version")) {
            printVersion();
        } else if (args[0].equals("--license")) {
            printLicense();
        }
    }

    private static void printLicense() {
        String gpl = "Monogatari  Copyright (C) 2014  Dmitry Kuznetsov aka kudzmi <kudzmi@mail.ru>\n"
                + "This program comes with ABSOLUTELY NO WARRANTY!\n"
                + "This is free software, and you are welcome to redistribute it;\n"
                + "see the source for copying conditions.\n";
        System.out.println(gpl);
    }

    private static void printVersion() {
        String ver = "Monogatari  v1.0 - static site generator by kudzmi\n";
        System.out.println(ver);
    }

    private static void out(String text) {
        System.out.println(text);
    }

}
