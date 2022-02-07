package be4rjp.crafterscut.api.util;

public class Spline2D {
    
    private final int N;
    private final Vec2d[] nodes;
    private final double[] z;
    
    public Spline2D(Vec2d[] nodes){
        if(nodes.length < 3) throw new IllegalArgumentException("The number of nodes must be at least 3.");
        
        this.N = nodes.length;
        this.nodes = nodes;
    
        Vec4d[] factors = new Vec4d[N];
        for(int i = 0; i < N; i++){
            factors[i] = new Vec4d();
        }
        
        for (int i = 1; i < N - 1; i++) {
            factors[i].a = nodes[i].x - nodes[i-1].x;
            factors[i].b = 2.0 * (nodes[i+1].x - nodes[i-1].x);
            factors[i].c = nodes[i+1].x - nodes[i].x;
            factors[i].d = 6.0 * ((nodes[i+1].y - nodes[i].y) / (nodes[i+1].x - nodes[i].x) - (nodes[i].y - nodes[i-1].y) / (nodes[i].x - nodes[i-1].x));
        }
        
        //Thomas
        Vec2d[] thomasFactor = new Vec2d[N];
        for(int i = 0; i < N; i++){
            thomasFactor[i] = new Vec2d();
        }
        
        thomasFactor[1].x = factors[1].b;
        thomasFactor[1].y = factors[1].d;
        for (int i = 2; i < N - 1; i++) {
            thomasFactor[i].x = factors[i].b - factors[i].a * factors[i-1].c / thomasFactor[i-1].x;
            thomasFactor[i].y = factors[i].d - factors[i].a * thomasFactor[i-1].y / thomasFactor[i-1].x;
        }
        
        this.z = new double[N];
        z[0]   = 0;
        z[N-1] = 0;
        z[N-2] = thomasFactor[N-2].y / thomasFactor[N-2].x;
        for (int i = N - 3; i >= 1; i--) {
            z[i] = (thomasFactor[i].y - factors[i].c * z[i + 1]) / thomasFactor[i].x;
        }
        
    }
    
    public double getPos(double index) {
        int k = -1;
        for (int i = 1; i < N; i++) {
            if (index <= nodes[i].x) {
                k = i - 1;
                break;
            }
        }
        if (k < 0) k = N - 1;
        
        double d1 = nodes[k+1].x - index;
        double d2 = index - nodes[k].x;
        double d3 = nodes[k+1].x - nodes[k].x;
        return (z[k] * Math.pow(d1, 3) + z[k+1] * Math.pow(d2, 3)) / (6.0 * d3) + (nodes[k].y / d3 - z[k] * d3 / 6.0) * d1 + (nodes[k+1].y / d3 - z[k+1] * d3 / 6.0) * d2;
    }
}
