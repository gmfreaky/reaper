package nl.sonware.opengltest.util;

import java.util.Random;

import nl.sonware.opengltest.Vector3;

// Noise3D.java by mazander, I do not own this class

/**
 * @author mazander
 */
public final class Noise3D {

        private final double[][][] noise;
        
        private final Vector3 waveLength;
        
        private final double amplitude;
        
        private final int size;

        private final double min;

        private final double max;
        
        public Noise3D(Random random, final int size, final Vector3 waveLength, final double min, final double max, final boolean gaussian) {
                this.size = size;
                this.waveLength = waveLength;
                this.min = min;
                this.max = max;
                this.amplitude = max - min;
                this.noise = new double[size][size][size];
                for (int i = 0; i < size; i++) {
                        for (int j = 0; j < size; j++) {
                                for (int k = 0; k < size; k++) {
                                        if(gaussian) {
                                                double r = 0.15f * random.nextGaussian();
                                                if(r < 0f) r = -r;
                                                if(r > 1f) r = 1f;
                                                noise[i][j][k] = min + amplitude * r;
                                        } else {
                                                noise[i][j][k] = min + amplitude * random.nextFloat();
                                        }
                                }
                        }
                }
        }
        
        public double getAmplitude() {
                return amplitude;
        }
        
        public double getMin() {
                return min;
        }
        
        public double getMax() {
                return max;
        }
        
        public Noise3D(Random random, final int size, final float waveLength, final double amplitude, final boolean gaussian) {
                this(random, size, new Vector3(waveLength, waveLength, waveLength), -0.5f * amplitude, 0.5f * amplitude, gaussian);
        }

        public Noise3D(Random random, final int size, final float strength, final boolean gaussian) {
                this(random, size, strength, strength, gaussian);
        }
        
        public final double getNoise(final double x, final double y, final double z) {
                // X
                double ax0 = x / waveLength.getX();
        int x0 = (int) ax0;
        if(ax0 < 0f) x0--;
        ax0 -= x0;
        x0 %= size; 
        if(x0 < 0) x0 += size;
        final int x1 = (x0 + 1) % size;
        final double ax1 = 1.0 - ax0;
        
        // Y
        double ay0 = y / waveLength.getY();
        int y0 = (int) ay0;
        if(ay0 < 0f) y0--;
        ay0 -= y0;
        y0 %= size; 
        if(y0 < 0) y0 += size;
        final int y1 = (y0 + 1) % size;
        final double ay1 = 1.0 - ay0;
        
        // Z
        double az0 = z / waveLength.getZ();
        int z0 = (int) az0;
        if(az0 < 0f) z0--;
        az0 -= z0;
        z0 %= size;
        if(z0 < 0) z0 += size;
        final int z1 = (z0 + 1) % size;
        final double az1 = 1.0 - az0;

       
        return Math.abs(noise[x0][y0][z0] * ax1 * ay1 * az1 +
                   noise[x0][y0][z1] * ax1 * ay1 * az0 +
               noise[x0][y1][z0] * ax1 * ay0 * az1 +
               noise[x0][y1][z1] * ax1 * ay0 * az0 +
               noise[x1][y0][z0] * ax0 * ay1 * az1 +
               noise[x1][y0][z1] * ax0 * ay1 * az0 +
               noise[x1][y1][z0] * ax0 * ay0 * az1 +
               noise[x1][y1][z1] * ax0 * ay0 * az0);
        }
}