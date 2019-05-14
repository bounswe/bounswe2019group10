const frequencyTest = require('./frequencyTest');

test('string returning working', async() => {
    const testResult = await frequencyTest();
    expect(testResult).toMatch('working');
});