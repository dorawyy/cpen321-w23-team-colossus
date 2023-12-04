const GoogleCalendar = require('../Interfaces/GoogleCalendar.js');
const inputs = require('./mockInputs.js');

jest.mock('googleapis', () => {
    const mockAuthClient = {
      getToken: jest.fn(),
      setCredentials: jest.fn(),
    };
    const mockCalendar = {
        events: {
            list: jest.fn(),
        }
    }
    return {
        google: {
            auth: {
                OAuth2: jest.fn(() => mockAuthClient),
            },
            calendar: jest.fn(() => mockCalendar),
        },
    };
});

describe('Testing google calendar API calls', () => {
    // successful get google calendar
    it('should get calendar given valid code', async () => {
        const calendarInstance = new GoogleCalendar();
        const code = 'validCode';

        calendarInstance.authClient.getToken.mockResolvedValue({ token: 'token object' });
        calendarInstance.calendar.events.list.mockResolvedValue({ data: { items: inputs.sampleEvents} } );

        const events = await calendarInstance.getCalendarEvents(code);

        expect(events).toStrictEqual(inputs.expectedEvents);
    });

    // failures case
    it('should fail on API exception', async () => {
        const calendarInstance = new GoogleCalendar();
        const code = 'invalidCode';

        calendarInstance.authClient.getToken.mockRejectedValue(new Error('Google API error'));
        calendarInstance.calendar.events.list.mockResolvedValue({ data: { items: inputs.sampleEvents} } );

        let events = null;
        try {
            events = await calendarInstance.getCalendarEvents(code);
        } catch (e) {
            expect(e).toBeDefined();
        }
        expect(events).toBeNull();
    })
})