#version 140

in vec2 position;
in mat4 modelViewMatrix;
in vec4 texOffsets;
in float blendFactor;
in vec2 atlasOffset;

out vec2 textureCoords1;
out vec2 textureCoords2;
out float blend;

uniform mat4 projectionMatrix;
uniform float numberOfRows;
uniform float numberOfAtlasRows;

void main(void) {

	vec2 textureCoords = position + vec2(0.5, 0.5);

	textureCoords.y = 1.0 - textureCoords.y;
	textureCoords /= numberOfRows;
	textureCoords1 = textureCoords + texOffsets.xy;
	textureCoords2 = textureCoords + texOffsets.zw;

	textureCoords1 /= numberOfAtlasRows;
	textureCoords1 += atlasOffset;
	textureCoords2 /= numberOfAtlasRows;
	textureCoords2 += atlasOffset;

	blend = blendFactor;

	gl_Position = projectionMatrix * modelViewMatrix * vec4(position, 0.0, 1.0);

}
