export interface IField {
    id?: number;
    name?: string;
    displayName?: string;
}

export class Field implements IField {
    constructor(public id?: number, public name?: string, public displayName?: string) {}
}
