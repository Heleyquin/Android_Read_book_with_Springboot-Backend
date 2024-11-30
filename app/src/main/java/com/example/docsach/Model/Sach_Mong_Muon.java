package com.example.docsach.Model;

import java.io.Serializable;
import java.util.Objects;

public class Sach_Mong_Muon implements Serializable {
    private Long id;
    private Reader readerWish;
    private Sach sachWish;

    public Sach_Mong_Muon() {
    }

    public Sach_Mong_Muon(Long id, Reader readerWish, Sach sachWish) {
        this.id = id;
        this.readerWish = readerWish;
        this.sachWish = sachWish;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Reader getReaderWish() {
        return readerWish;
    }

    public void setReaderWish(Reader readerWish) {
        this.readerWish = readerWish;
    }

    public Sach getSachWish() {
        return sachWish;
    }

    public void setSachWish(Sach sachWish) {
        this.sachWish = sachWish;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sach_Mong_Muon that = (Sach_Mong_Muon) o;
        return Objects.equals(readerWish, that.readerWish) && Objects.equals(sachWish, that.sachWish);
    }

    @Override
    public int hashCode() {
        return Objects.hash(readerWish, sachWish);
    }

    public static final class Sach_Mong_MuonBuilder {
        private Sach_Mong_Muon sach_Mong_Muon;

        public Sach_Mong_MuonBuilder() {
            sach_Mong_Muon = new Sach_Mong_Muon();
        }

        public Sach_Mong_MuonBuilder(Sach_Mong_Muon other) {
            this.sach_Mong_Muon = other;
        }

        public static Sach_Mong_MuonBuilder aSach_Mong_Muon() {
            return new Sach_Mong_MuonBuilder();
        }

        public Sach_Mong_MuonBuilder withId(Long id) {
            sach_Mong_Muon.setId(id);
            return this;
        }

        public Sach_Mong_MuonBuilder withReaderWish(Reader readerWish) {
            sach_Mong_Muon.setReaderWish(readerWish);
            return this;
        }

        public Sach_Mong_MuonBuilder withSachWish(Sach sachWish) {
            sach_Mong_Muon.setSachWish(sachWish);
            return this;
        }

        public Sach_Mong_Muon build() {
            return sach_Mong_Muon;
        }
    }
}
