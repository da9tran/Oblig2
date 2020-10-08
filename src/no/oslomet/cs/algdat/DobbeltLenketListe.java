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

    public Liste<T> subliste(int fra, int til){
        fratilKontroll(antall, fra, til);

        Liste<T> liste = new DobbeltLenketListe<>();

        int lengde = til - fra;

        if (lengde < 1) {
            return liste;
        }

        Node<T> p = finnNode(fra);

        while (lengde > 0) {
            liste.leggInn(p.verdi);
            p = p.neste;
            lengde--;
        }
        return liste;
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
    public void leggInn(int indeks, T verdi){
        Objects.requireNonNull(verdi, "Null verdier er ikke tillatt!");

        indeksKontroll(indeks, true);

        if (indeks > antall){
            throw new IndexOutOfBoundsException("Indeks er større enn antall noder!");
        }

        else if (indeks < 0) throw new IndexOutOfBoundsException("Negativ indeks er ikke tillat!");

        if (antall == 0 && indeks == 0){
            hode = hale = new Node<T>(verdi, null, null);
        }

        else if (indeks == antall) {
            hale = new Node<T>(verdi, hale, null);
            hale.forrige.neste = hale;
        }

        else if (indeks == 0) {
            hode = new Node<T>(verdi, null, hode);
            hode.neste.forrige = hode;
        }

        else {
            Node<T> node = hode;

            for (int i = 0; i < indeks; i++) node = node.neste;{
                node = new Node<T>(verdi, node.forrige, node);
            }
            node.neste.forrige = node.forrige.neste = node;
        }
        antall++;
        endringer++;
    }

    @Override
    public boolean inneholder(T verdi) {
        return indeksTil(verdi) != -1;
    }

    @Override
    public T hent(int indeks) {
        Node<T> p = finnNode(indeks);
        return p.verdi;
    }

    @Override
    public int indeksTil(T verdi) {
        if (verdi == null) return -1;

        Node<T> p = hode;
        for (int indeks = 0; indeks < antall; indeks++, p = p.neste){
            if (p.verdi.equals(verdi)) return indeks;
        }
        return -1;
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
        if (verdi == null){
            return false;
        }

        Node<T> p = hode;

        if (verdi.equals(p.verdi)){
            if (p.neste != null){
                hode = p.neste;
                hode.forrige = null;
            }

            else {
                hode = null;
                hale = null;
            }

            antall--;
            endringer++;
            return true;
        }

        p = hale;
        if (verdi.equals(p.verdi)){
            hale = p.forrige;
            hale.neste = null;
            antall--;
            endringer++;
            return true;
        }

        p = hode.neste;
        for (; p != null; p = p.neste){
            if (verdi.equals(p.verdi)){
                p.forrige.neste = p.neste;
                p.neste.forrige = p.forrige;
                antall --;
                endringer++;
                return true;
            }
        }
        return false;
    }

    @Override
    public T fjern(int indeks) {
        indeksKontroll(indeks, false);

        Node<T> p = hode;
        T verdi;

        if (indeks == 0){
            verdi = p.verdi;

            if (p.neste != null){
                hode = p.neste;
                hode.forrige = null;
            }

            else {
                hode = null;
                hale = null;
            }
        }

        else if (indeks == antall - 1){
            p = hale;
            verdi = hale.verdi;

            hale = p.forrige;
            hale.neste = null;
        }

        else {
            for (int i = 0; i < indeks; i++){
                p = p.neste;
            }
            verdi = p.verdi;
            p.forrige.neste = p.neste;
            p.neste.forrige = p.forrige;
        }
        antall--;
        endringer++;
        return verdi;
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
        return new DobbeltLenketListeIterator();
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
            if (!hasNext()) throw new NoSuchElementException("Ingen verder!");

            if (endringer != iteratorendringer) throw new ConcurrentModificationException("Listen er endret!");

            T tempverdi = denne.verdi;
            denne = denne.neste;

            fjernOK = true;

            return tempverdi;
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


