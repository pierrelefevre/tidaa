/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package f.f1;

import java.util.Objects;

/**
 * @author bfelt
 */
public class DirectoryEntry {

    public String name;
    public String number;

    public DirectoryEntry(String name, String number) {
        this.name = name;
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DirectoryEntry that = (DirectoryEntry) o;
        return Objects.equals(name, that.name);
    }
}
