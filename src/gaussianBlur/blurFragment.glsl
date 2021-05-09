#version 150

#define KERNEL_SIZE 11

out vec4 out_color;

in vec2 blurTextureCoords[KERNEL_SIZE];

uniform sampler2D originalTexture;

void main(void) {
	
	out_color = vec4(0.0);
	out_color += texture(originalTexture, blurTextureCoords[0]) * 0.0093;
	out_color += texture(originalTexture, blurTextureCoords[1]) * 0.028002;
	out_color += texture(originalTexture, blurTextureCoords[2]) * 0.065984;
	out_color += texture(originalTexture, blurTextureCoords[3]) * 0.121703;
	out_color += texture(originalTexture, blurTextureCoords[4]) * 0.175713;
	out_color += texture(originalTexture, blurTextureCoords[5]) * 0.198596;
	out_color += texture(originalTexture, blurTextureCoords[6]) * 0.175713;
	out_color += texture(originalTexture, blurTextureCoords[7]) * 0.121703;
	out_color += texture(originalTexture, blurTextureCoords[8]) * 0.065984;
	out_color += texture(originalTexture, blurTextureCoords[9]) * 0.028002;
	out_color += texture(originalTexture, blurTextureCoords[10]) * 0.0093;
}
