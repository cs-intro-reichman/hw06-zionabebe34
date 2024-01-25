// This class uses the Color class, which is part of a package called awt,
// which is part of Java's standard class library.
import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {
	    
		//// Hide / change / add to the testing code below, as needed.
		
		//--------------------------
		// Tests the reading and printing of an image:	
		Color[][] tinypic = read("tinypic.ppm");
		//print(tinypic);
		//System.out.println();
		//--------------------------

        //--------------------------
		// Creates an image which will be the result of various 
		// image processing operations:
		Color[][] imageOut;
		//--------------------------

		//--------------------------
		// Tests the horizontal flipping of an image:
		//imageOut = flippedVertically(tinypic);
		//System.out.println();
		//print(imageOut);
		//--------------------------
		//imageOut = scaled(tinypic, x , y); 
		//print(imageOut);
		
		//// Write here whatever code you need in order to test your work.
		//// You can reuse / overide the contents of the imageOut array.
	}

	/** Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file. */
	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		// Reads the file header, ignoring the first and the third lines.
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		// Creates the image array
		Color[][] image = new Color[numRows][numCols];
		// Reads the RGB values from the file, into the image array. 
		// For each pixel (i,j), reads 3 values from the file,
		// creates from the 3 colors a new Color object, and 
		// makes pixel (i,j) refer to that object.
		//// Replace the following statement with your code.
		for ( int x = 0; x < numRows; x++){
			for ( int y = 0; y < numCols; y++){
				int red = in.readInt();
				int green = in.readInt(); 
				int blue = in.readInt(); 
				Color pixel  = new Color (red, green, blue); 
				image[x][y] = pixel;  
			}
		}
		return image;
	}

    // Prints the RGB values of a given color.
	private static void print(Color c) {
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting image.
	private static void print(Color[][] image) {
		//// Replace this comment with your code
		for (int x = 0; x < image.length; x++) {
			for ( int y = 0; y < image[x].length; y++) {

				int red = image[x][y].getRed(); 
				int green = image[x][y].getGreen();
				int blue = image[x][y].getBlue(); 
				System.out.printf("(%3s, %3s, %3s)", red, green , blue);
			}
			System.out.println();
		}
	}
	
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {
		Color[][] imageHor = new Color [image.length][image[0].length]; 
		for ( int x = 0; x < image.length; x++) {
			int col = 0; //for the specific index 
			for ( int y = image[0].length-1; y >= 0; y--){
				imageHor[x][col] = image[x][y]; 
				col++; 
			}
		}
		return imageHor;
	}
	
	/**
	 * Returns an image which is the vertically flipped version of the given image. 
	 */
	public static Color[][] flippedVertically(Color[][] image){
		Color[][] imageVer = new Color [image.length][image[0].length];  
		int row = 0; 
		for ( int x = image.length -1 ; x >= 0 ; x--){
			for ( int y = 0; y < image[0].length; y++) {
				imageVer[row][y] = image[x][y]; 
			}
			row++;
			System.out.println();
		}
		return imageVer;
	}
	
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
	public static Color luminance(Color pixel) {
		int red = pixel.getRed(); 
		int green = pixel.getGreen(); 
		int blue = pixel.getBlue(); 
		int lum = (int) ((0.299 * red) + (0.587 * green) + (0.114 * blue)); 
		pixel = new Color(lum, lum , lum);

		return pixel;
	}
	
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {
		Color[][] greyColor = new Color[image.length][image[0].length]; 
		for ( int x = 0; x < image.length; x++){
			for ( int y = 0; y < image[0].length; y++) {
				Color pixel = image[x][y]; 
				greyColor[x][y] = luminance(pixel);
			}
		}
		return greyColor;
	}	
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) {
		Color[][] scaled = new Color[height][width];
		double xRadio = (image[0].length) / width;
		double yRadio = (image.length) / height; 
		for ( int x = 0; x < scaled.length; x++ ) {
			for ( int y = 0; y < scaled[0].length; y++) {
				scaled[x][y] = image[(int)( x * xRadio)][(int) (y * yRadio)];
			}
		}
		return scaled;
	}
	
	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
		int averageRed =(int) ((alpha * c1.getRed()) + ( (1-alpha) * c2.getRed() )); 
		int averageBlue =(int) ((alpha * c1.getBlue()) + ( (1-alpha) * c2.getBlue() ));
		int averageGreen =(int) ((alpha * c1.getGreen()) + ( (1-alpha) * c2.getGreen() ));
	
		Color averageColor = new Color(averageRed, averageGreen, averageBlue); 

		return averageColor;
	}
	
	/**
	 * Cosntructs and returns an image which is the blending of the two given images.
	 * The blended image is the linear combination of (alpha) part of the first image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
		Color[][] blended = new Color[image1.length][image1[0].length]; 
		for ( int x = 0; x < image1.length; x++) {
			for ( int y = 0; y < image1[0].length; y++) {
				blended[x][y] = blend(image1[x][y], image2[x][y], alpha);
			}
		}
		return blended;
	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	public static void morph(Color[][] source, Color[][] target, int n) {
		Color[][] blended = new Color[source.length][source[0].length]; 
		for ( int i = 0; i < n; i++) {
			blended = blend(source, target, (double) i / (n - 1));
			display(blended);
			StdDraw.pause(500); 
		}
	}
	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(height, width);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}

