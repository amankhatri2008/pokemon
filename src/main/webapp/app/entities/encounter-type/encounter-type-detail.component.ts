import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEncounterType } from 'app/shared/model/encounter-type.model';

@Component({
  selector: 'jhi-encounter-type-detail',
  templateUrl: './encounter-type-detail.component.html'
})
export class EncounterTypeDetailComponent implements OnInit {
  encounterType: IEncounterType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ encounterType }) => {
      this.encounterType = encounterType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
