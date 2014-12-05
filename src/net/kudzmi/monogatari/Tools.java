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

import java.io.*;

/**
 * @author Dmitry Kuznetsov aka 「kudzmi」
 */
public class Tools {

    public static String readFile(File theFile) {
        String content = "";
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(theFile), "UTF8"))) {
            String str;
            while ((str = in.readLine()) != null) {
                content += str + "\n";
            }
            in.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return content;
    }

    public static void writeFile(File file, String content) {
        try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF8"))) {
            out.write(content);
            out.flush();
            out.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


}
