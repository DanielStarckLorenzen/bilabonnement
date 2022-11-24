package com.example.bilabonnement.model;

public class Damages {

    private int damageId;
    private String problem;
    private String frameNumber;
    private int vehicleNumber;

    public Damages(int damageId, String problem, String frameNumber, int vehicleNumber) {
        this.damageId = damageId;
        this.problem = problem;
        this.frameNumber = frameNumber;
        this.vehicleNumber = vehicleNumber;
    }

    public Damages() {
    }

    public int getDamageId() {
        return damageId;
    }

    public void setDamageId(int damageId) {
        this.damageId = damageId;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(String frameNumber) {
        this.frameNumber = frameNumber;
    }

    public int getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(int vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    @Override
    public String toString() {
        return "Damages{" +
                "damageId=" + damageId +
                ", problem='" + problem + '\'' +
                ", frameNumber='" + frameNumber + '\'' +
                ", vehicleNumber=" + vehicleNumber +
                '}';
    }
}
