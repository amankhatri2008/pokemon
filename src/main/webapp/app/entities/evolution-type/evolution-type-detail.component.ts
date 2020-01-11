import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEvolutionType } from 'app/shared/model/evolution-type.model';

@Component({
  selector: 'jhi-evolution-type-detail',
  templateUrl: './evolution-type-detail.component.html'
})
export class EvolutionTypeDetailComponent implements OnInit {
  evolutionType: IEvolutionType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ evolutionType }) => {
      this.evolutionType = evolutionType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
