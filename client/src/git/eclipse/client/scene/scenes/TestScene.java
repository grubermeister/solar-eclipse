package git.eclipse.client.scene.scenes;

import git.eclipse.client.EclipseClient;
import git.eclipse.client.graphics.ui.Menu;
import git.eclipse.client.scene.SceneAdapter;

public class TestScene extends SceneAdapter {

    private Menu winTest;

    @Override
    public void show() {
        winTest = new Menu("chat");

        winTest.titlebar = false;
        winTest.resizable = false;
        winTest.moveable = false;

        winTest.setDimension(600, 200);
        winTest.setPosition(0, EclipseClient.Height() - winTest.getDimension().y);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void update(double dt) {
        // TODO: Update Code
    }

    @Override
    public void render() {
        // TODO: Render Code
    }

    @Override
    public void imgui() {
        winTest.update();
    }

    @Override
    public void dispose() {
        // TODO: Dispose Code
    }

}
