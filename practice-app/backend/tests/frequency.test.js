const frequencyTest = require('./similarToTest');

test('string returning working', async() => {
    const testResult = await frequencyTest();
    expect(testResult).toMatch('working');
});