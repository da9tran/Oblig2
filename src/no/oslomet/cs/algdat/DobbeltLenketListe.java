package no.oslomet.cs.algdat;


////////////////// class DobbeltLenketListe //////////////////////////////


import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;



public class DobbeltLenketListe<T> implements Liste<T> {

    /**
     * Node class
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen

    public DobbeltLenketListe() {
        hode = hale = null;
        antall = 0;
        endringer = 0;
    }

    public DobbeltLenketListe(T[] a) {
        this();

        Objects.requireNonNull(a, "a = null");

        hode = hale = new Node<>(null);

        for (T verdi : a){
            if (verdi != null){
                hale = hale.neste = new Node <> (verdi,hale,null);
                antall++;
            }
        }
        if (antall==0) hode = hale = null;
        else (hode = hode.neste).forrige=null;
    }

    @Override
    public int antall() {
        return antall; }

    @Override
    public boolean tom() {
        return antall == 0;
    }

    @Override
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Null verdier ikke tillat!");

        if (antall == 0) {
            hode = hale = new Node<>(verdi, null, null);
        }
        else {
            hale = hale.neste = new Node<>(verdi, hale, null);
        }
        antall++;
        endringer++;
        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean inneholder(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T hent(int indeks) {
        Node<T> p = finnNode(indeks);
        return p.verdi;
    }

    @Override
    public int indeksTil(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        Objects.requireNonNull(nyverdi);

        Node<T> p = finnNode(indeks);

        T gammelverdi = p.verdi;
        endringer++;

        p.verdi = nyverdi;

        return gammelverdi;
    }

    @Override
    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T fjern(int indeks) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void nullstill() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        Node<T> current = hode;
        StringBuilder s = new StringBuilder();
        s.append("[");

        if (tom()) {
            s.append("]");
            return s.toString();
        }
        else {
            s.append(current.verdi);
            current = current.neste;
            while (current != null) {
                s.append(", ");
                s.append(current.verdi);
                current = current.neste;
            }
        }
        s.append("]");
        return s.toString();
    }

    public String omvendtString() {
        Node<T> current = hale;
        StringBuilder s = new StringBuilder();
        s.append("[");

        if (tom()) {
            s.append("]");
            return s.toString();
        }
        else {
            s.append(current.verdi);
            current = current.forrige;
            while (current != null) {
                s.append(", ");
                s.append(current.verdi);
                current = current.forrige;
            }
        }
        s.append("]");
        return s.toString();
    }

    //hjelpemetode for 3a
    private Node<T> finnNode(int indeks){
        indeksKontroll(indeks, false);

        Node<T> p;

        if (indeks < antall / 2) {
            p = hode;
            for (int i = 0; i < indeks; i++) {
                p = p.neste;
            }
            return p;
        }
        else {
            p = hale;
            for (int i = antall - 1; i > indeks; i--) {
                p = p.forrige;
            }
            return p;
        }
    }

    //hjelpemetode for 3b
    private void fratilKontroll(int antall, int fra, int til){
        if (fra < 0 || til > antall){
            throw new IndexOutOfBoundsException();
        }
        if (fra > til) {
            throw new IllegalArgumentException();
        }
    }


    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    public Iterator<T> iterator(int indeks) {
        throw new UnsupportedOperationException();
    }

    private class DobbeltLenketListeIterator implements Iterator<T>
    {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator(){
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks){
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext(){
            return denne != null;
        }

        @Override
        public T next(){
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove(){
            throw new UnsupportedOperationException();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException();
    }

} // class DobbeltLenketListe


