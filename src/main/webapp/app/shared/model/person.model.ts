export const enum PersonStatus {
    ENABLE = 'ENABLE',
    DISABLE = 'DISABLE'
}

export interface IPerson {
    id?: number;
    name?: string;
    age?: number;
    status?: PersonStatus;
}

export class Person implements IPerson {
    constructor(public id?: number, public name?: string, public age?: number, public status?: PersonStatus) {}
}
