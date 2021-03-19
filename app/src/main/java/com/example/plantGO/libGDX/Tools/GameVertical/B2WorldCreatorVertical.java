package com.example.plantGO.libGDX.Tools.GameVertical;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.example.plantGO.libGDX.LibGDXLaunchers.GameVertical;
import com.example.plantGO.Modele;
import com.example.plantGO.libGDX.Scenes.GameVertical.HudVertical;
import com.example.plantGO.libGDX.Screens.GameVertical.PlayScreenVertical;
import com.example.plantGO.libGDX.Sprites.Ennemies.GameVertical.Dechet;
import com.example.plantGO.libGDX.Sprites.Ennemies.EnnemiVertical;
import com.example.plantGO.libGDX.Sprites.Ennemies.GameVertical.Serpent;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

public class B2WorldCreatorVertical {
    private Array<Serpent> serpents;
    private Array<Dechet> dechets;

    public B2WorldCreatorVertical(PlayScreenVertical screen) {
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
            //bdef.position.set((rect.getX() + rect.getWidth() / 2), (rect.getY() + rect.getHeight() / 2));
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / GameVertical.PPM, (rect.getY() + rect.getHeight() / 2) / GameVertical.PPM);

            body = world.createBody(bdef);

            //shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
            shape.setAsBox(rect.getWidth() / 2 / GameVertical.PPM, rect.getHeight() / 2 / GameVertical.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //create BloqueurEnnemiTerrestre bodies/fixtures
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;

            bdef.position.set((rect.getX() + rect.getWidth() / 2) / GameVertical.PPM, (rect.getY() + rect.getHeight() / 2) / GameVertical.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / GameVertical.PPM, rect.getHeight() / 2 / GameVertical.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = GameVertical.BLOQUEURENNEMITERRESTRE_BIT;
            body.createFixture(fdef);
        }

        //créer les serpents
        serpents = new Array<Serpent>();
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            serpents.add(new Serpent(screen, rect.getX() / GameVertical.PPM, rect.getY() / GameVertical.PPM));
        }

        //créer les dechets à collecter
        dechets = new Array<Dechet>();
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            dechets.add(new Dechet(screen, rect.getX() / GameVertical.PPM, rect.getY() / GameVertical.PPM));
            Modele.compteurDechetCollecte++;
            HudVertical.dechetsRestants(1);
        }
    }

    public Array<EnnemiVertical> getEnnemies(){
        Array<EnnemiVertical> enemies = new Array<EnnemiVertical>();
        enemies.addAll(serpents);
        enemies.addAll(dechets);
        return enemies;
    }
}