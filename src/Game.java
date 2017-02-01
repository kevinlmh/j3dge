/**
 * Created by kevin on 1/31/17.
 */
public class Game {
    private Mesh mesh;
    private Shader shader;

    public Game() {
        this.mesh = new Mesh();
        shader = new Shader();

        Vector3f[] data = new Vector3f[]{
                new Vector3f(-1, -1, 0),
                new Vector3f(0, 1, 0),
                new Vector3f(1, -1, 0)
        };

        mesh.addVertices(data);

        shader.addVertexShader(ResourceLoader.loadShader("basicVertex.vs"));
        shader.addFragmentShader(ResourceLoader.loadShader("basicFragment.fs"));
        shader.compileShader();
    }

    public void handleInput() {

    }

    public void render() {
        shader.bind();
        mesh.draw();
    }
}
