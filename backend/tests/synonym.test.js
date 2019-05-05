const synonymTest = require('./synonymTest');

test('string returning working', () => {
    expect(synonymTest()).toMatch('working');
});