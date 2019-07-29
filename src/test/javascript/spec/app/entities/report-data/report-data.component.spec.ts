/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ResearchDemoTestModule } from '../../../test.module';
import { ReportDataComponent } from 'app/entities/report-data/report-data.component';
import { ReportDataService } from 'app/entities/report-data/report-data.service';
import { ReportData } from 'app/shared/model/report-data.model';

describe('Component Tests', () => {
    describe('ReportData Management Component', () => {
        let comp: ReportDataComponent;
        let fixture: ComponentFixture<ReportDataComponent>;
        let service: ReportDataService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResearchDemoTestModule],
                declarations: [ReportDataComponent],
                providers: []
            })
                .overrideTemplate(ReportDataComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ReportDataComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReportDataService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ReportData(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.reportData[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
