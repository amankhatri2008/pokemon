import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICostToEvolve } from 'app/shared/model/cost-to-evolve.model';

@Component({
  selector: 'jhi-cost-to-evolve-detail',
  templateUrl: './cost-to-evolve-detail.component.html'
})
export class CostToEvolveDetailComponent implements OnInit {
  costToEvolve: ICostToEvolve | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ costToEvolve }) => {
      this.costToEvolve = costToEvolve;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
