from playwright.sync_api import sync_playwright
from urllib.parse import urlencode
import re

USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36"
KEYWORD='applikationsentwickler'
MAX_PAGES = 10
REGIONS = {
    'st.gallen': 17,
    'winterthur': 18,
    'thurgau': 21,
    'schaffhausen': 25
}

def build_url(page: int, keyword: str, regions: list[int]) -> str:
    params = [('term', keyword), ('page', page)]

    for region in regions:
        params.append(('region', region))

    return f'https://www.jobs.ch/de/stellenangebote/?{urlencode(params)}'

def scrape(keyword=KEYWORD, max_pages=MAX_PAGES, regions=list(REGIONS.values())) -> list:
    all_jobs = []

    with sync_playwright() as p:
        browser = p.chromium.launch()
        context = browser.new_context(
            user_agent=USER_AGENT,
            viewport={'width': 1920, 'height': 1080},
            locale='de-CH',
        )
        page = context.new_page()

        temp_url = build_url(1, keyword, regions)

        page.goto(temp_url)
        page.wait_for_timeout(3000)

        header = page.query_selector('[data-cy="page-header"]')
        total_jobs = int(re.search(r'\d+', header.inner_text()).group())

        jobs_per_page = 20
        total_pages = (total_jobs // jobs_per_page) + 1

        print(total_jobs)

        for current_page in range(1, min(total_pages, max_pages) + 1):
            if current_page > 1:
                URL = build_url(current_page, keyword, regions)

                print(f'\nSeite {current_page}: {URL}')

                page.goto(URL)
                page.wait_for_timeout(3000)
            else:
                print(f'\nSeite 1: {temp_url}')

            cards = page.query_selector_all('[data-cy="vacancy-serp-item"]')
            print(f"{len(cards)} Karten gefunden")

            if len(cards) == 0:
                print(" keine Karten mehr")
                break

            for card in cards:
                title = card.query_selector('span.fw_bold')

                p_tags = card.query_selector_all('p.textStyle_caption1')
                published = p_tags[0] if len(p_tags) > 0 else None
                location = p_tags[1] if len(p_tags) > 1 else None

                company = card.query_selector('p.textStyle_caption1.c_gray\\.700.fw_bold')

                inner = card.query_selector('[data-cy^="serp-item-"]')
                job_id = inner.get_attribute('data-cy').replace('serp-item-', '') if inner else None
                url = f"https://www.jobs.ch/de/stellenangebote/detail/{job_id}" if job_id else "N/A"

                job = {
                    'title': title.inner_text() if title else 'N/A',
                    'location': location.inner_text() if location else 'N/A',
                    'company': company.inner_text() if company else 'N/A',
                    'published': published.inner_text() if published else 'N/A',
                }

                all_jobs.append(job)

                print('Titel: ', title.inner_text() if title else 'N/A')
                print('Ort: ', location.inner_text() if location else 'N/A')
                print('Firma: ', company.inner_text() if company else 'N/A')
                print('Veröffentlicht: ', published.inner_text() if published else 'N/A')
                print("URL:  ", url)
                print('---\n')

        browser.close()

    return all_jobs
