const similarToTest = require('./similarToTest');

test('string returning working', async() => {
    const testResult = await similarToTest();
    expect(testResult).toMatch('working');
});