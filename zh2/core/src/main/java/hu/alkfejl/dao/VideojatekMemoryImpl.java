package hu.alkfejl.dao;

import hu.alkfejl.model.Videojatek;

import java.util.ArrayList;
import java.util.List;

public class VideojatekMemoryImpl implements VideojatekDAO {
    private ArrayList<Videojatek> db = new ArrayList<>();

    public VideojatekMemoryImpl(String s) {}

    @Override
    public boolean add(Videojatek videojatek) {
        var res = db.stream().filter( z ->
                videojatek.getCim().equals(z.getCim())
        ).toList();
        if ( ! res.isEmpty() ) { return false; }

        db.add(videojatek);

        return true;
    }

    @Override
    public List<Videojatek> find(Videojatek filter) {
        return db.stream().filter( z ->
                (filter.getCim() == null || filter.getCim().equals(z.getCim())) &&
                (filter.getFejleszto() == null || filter.getFejleszto().equals(z.getFejleszto())) &&
                (filter.getAr() == null || filter.getAr().equals(z.getAr()) )&&
                (filter.getBesorolas() == null || filter.getBesorolas().equals(z.getBesorolas()))
        ).toList();
    }

    @Override
    public boolean delete(Videojatek videojatek) {
        return db.removeIf(x -> x.getCim().equals(videojatek.getCim()));
    }

    @Override
    public boolean update(Videojatek videojatek) {
        var res = db.stream().filter( z ->
                videojatek.getCim().equals(z.getCim())
        ).toList();
        if ( res.size() != 1 ) { return false; }
        res.get(0).setBesorolas(videojatek.getBesorolas());
        return true;
    }
}
