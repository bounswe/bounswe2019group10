const hasTypesTest = require('./hasTypesTest');

test('string returning working', async() => {
    const testResult = await hasTypesTest();
    expect(testResult).toMatch('working');
});