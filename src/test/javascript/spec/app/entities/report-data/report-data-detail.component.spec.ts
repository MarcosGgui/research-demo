/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResearchDemoTestModule } from '../../../test.module';
import { ReportDataDetailComponent } from 'app/entities/report-data/report-data-detail.component';
import { ReportData } from 'app/shared/model/report-data.model';

describe('Component Tests', () => {
    describe('ReportData Management Detail Component', () => {
        let comp: ReportDataDetailComponent;
        let fixture: ComponentFixture<ReportDataDetailComponent>;
        const route = ({ data: of({ reportData: new ReportData(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResearchDemoTestModule],
                declarations: [ReportDataDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ReportDataDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ReportDataDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.reportData).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
