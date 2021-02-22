package com.example.test_libgdxintoandroid.Tools.GameHorizontal;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.example.test_libgdxintoandroid.Screens.GameHorizontal.PlayScreenHorizontal;
import com.example.test_libgdxintoandroid.Sprites.Ennemies.EnnemiHorizontal;
import com.example.test_libgdxintoandroid.Sprites.Ennemies.GameHorizontal.Crabe;
import com.example.test_libgdxintoandroid.LibGDXLaunchers.GameHorizontal;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.example.test_libgdxintoandroid.Sprites.Ennemies.GameHorizontal.PlanteCarnivore;

public class B2WorldCreatorHorizontal {
    private Array<PlanteCarnivore> PlantesCarnivores;
    private Array<Crabe> crabes;

    public B2WorldCreatorHorizontal(PlayScreenHorizontal screen) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        // Create body and fixtures variables
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //create ground bodies/fixtures
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / GameHorizontal.PPM, (rect.getY() + rect.getHeight() / 2) / GameHorizontal.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / GameHorizontal.PPM, rect.getHeight() / 2 / GameHorizontal.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //create BloqueurEnnemiTerrestre bodies/fixtures
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;

            bdef.position.set((rect.getX() + rect.getWidth() / 2) / GameHorizontal.PPM, (rect.getY() + rect.getHeight() / 2) / GameHorizontal.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / GameHorizontal.PPM, rect.getHeight() / 2 / GameHorizontal.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = GameHorizontal.BLOQUEURENNEMITERRESTRE_BIT;
            body.createFixture(fdef);
        }

        //create all goombas
        crabes = new Array<Crabe>();
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            crabes.add(new Crabe(screen, rect.getX() / GameHorizontal.PPM, rect.getY() / GameHorizontal.PPM));
        }

        //créer les plantes carnivores
        PlantesCarnivores = new Array<PlanteCarnivore>();
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            PlantesCarnivores.add(new PlanteCarnivore(screen, rect.getX() / GameHorizontal.PPM, rect.getY() / GameHorizontal.PPM));
        }

    }

    public Array<EnnemiHorizontal> getEnnemies(){
        Array<EnnemiHorizontal> enemies = new Array<EnnemiHorizontal>();
        enemies.addAll(crabes);
        enemies.addAll(PlantesCarnivores);
        return enemies;
    }
}