const definitionTest = require('./definitionTest');

test('string returning working', async () => {
    const testResult = await definitionTest();
    expect(testResult).toMatch('working');
});