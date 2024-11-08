module.exports = {
    preset: 'ts-jest',
    testEnvironment: 'jsdom',
    transform: {
      '^.+\\.(ts|tsx)$': 'ts-jest', // Add .ts and .tsx files
    },
    moduleFileExtensions: ['ts', 'tsx', 'js', 'json', 'node'],
    transformIgnorePatterns: ['/node_modules/'], // Ignore node_modules for transformation
  };
  