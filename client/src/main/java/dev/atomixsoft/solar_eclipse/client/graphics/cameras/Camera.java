package dev.atomixsoft.solar_eclipse.client.graphics.cameras;

import org.joml.Matrix4f;
import org.joml.Vector3f;


public class Camera {
    protected Matrix4f m_View, m_Projection, m_Combined;
    protected Vector3f m_Position, m_Rotation, m_Scale;
    protected float m_Zoom;


    protected Camera() {
        this.m_View = new Matrix4f();
        this.m_Projection = new Matrix4f();
        this.m_Combined = new Matrix4f();
        this.m_Position = new Vector3f(0.0f);
        this.m_Rotation = new Vector3f(0.0f);
        this.m_Scale = new Vector3f(1.0f);

        this.m_Zoom = 1.0f;
    }

    public void setPosition(Vector3f position) {
        this.m_Position.set(position);
    }

    public void setRotation(Vector3f rotation) {
        this.m_Rotation.set(rotation);
    }

    public void setScale(Vector3f scale) {
        this.m_Scale.set(scale);
    }

    public void setScale(float scalar) {
        this.setScale(new Vector3f(scalar));
    }

    public void setZoom(float zoom) {
        this.m_Zoom = zoom;
    }

    public Vector3f getPosition() {
        return this.m_Position;
    }

    public Vector3f getRotation() {
        return this.m_Rotation;
    }

    public Vector3f getScale() {
        return this.m_Scale;
    }

    public float getZoom() {
        return this.m_Zoom;
    }

    public Matrix4f getView() {
        return this.m_View;
    }

    public Matrix4f getProjection() {
        return this.m_Projection;
    }

    public Matrix4f getCombined() {
        this.m_Combined.set(getView()).mul(getProjection());

        return this.m_Combined;
    }
}
