#version 330

in vec2 pass_textureCoords;

out vec4 out_Color;

uniform vec3 color;
uniform sampler2D fontAtlas;

uniform float width;
uniform float edge;

uniform float borderWidth;
uniform float borderEdge;

uniform vec2 offset;

uniform vec3 outlineColor;

float smoothlyStep(float edge0, float edge1, float x) {
	float t = clamp((x - edge0) / (edge1 - edge0), 0.0, 1.0);
	return t * t * (3.0 - 2.0 * t);
}

void main(void) {

	float distance = 1.0 - texture(fontAtlas, pass_textureCoords).a;
	float alpha = 1.0 - smoothlyStep(width, width + edge, distance);

	float distance2 = 1.0 - texture(fontAtlas, pass_textureCoords + offset).a;
	float outlineAlpha = 1.0 - smoothlyStep(borderWidth, borderWidth + borderEdge, distance2);

	float finalAlpha = alpha + (1.0 - alpha) * outlineAlpha;
	vec3 finalColor = mix(outlineColor, color, alpha / finalAlpha);

	out_Color = vec4(finalColor, finalAlpha);
}
