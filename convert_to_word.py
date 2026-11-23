import docx
from docx.shared import Inches, Pt, RGBColor
from docx.enum.text import WD_ALIGN_PARAGRAPH
from docx.enum.table import WD_TABLE_ALIGNMENT
from docx.oxml import parse_xml
from docx.oxml.ns import nsdecls

def set_cell_background(cell, fill_hex):
    tcPr = cell._tc.get_or_add_tcPr()
    shd = parse_xml(f'<w:shd {nsdecls("w")} w:fill="{fill_hex}"/>')
    tcPr.append(shd)

def create_word_report():
    doc = docx.Document()
    
    # Standard 1-inch academic page margins
    for section in doc.sections:
        section.top_margin = Inches(1.0)
        section.bottom_margin = Inches(1.0)
        section.left_margin = Inches(1.0)
        section.right_margin = Inches(1.0)
        
    with open('KuralVerse_Project_Report.md', 'r', encoding='utf-8') as f:
        lines = f.readlines()
        
    in_code_block = False
    code_lines = []
    in_table = False
    table_lines = []
    
    for line in lines:
        raw_line = line.rstrip('\n')
        stripped = raw_line.strip()
        
        # Handle Code Blocks
        if stripped.startswith('```'):
            if in_code_block:
                p = doc.add_paragraph()
                p.paragraph_format.space_before = Pt(6)
                p.paragraph_format.space_after = Pt(6)
                p.paragraph_format.left_indent = Inches(0.2)
                run = p.add_run('\n'.join(code_lines))
                run.font.name = 'Consolas'
                run.font.size = Pt(9.5)
                run.font.color.rgb = RGBColor(30, 41, 59)
                code_lines = []
                in_code_block = False
            else:
                in_code_block = True
                code_lines = []
            continue
            
        if in_code_block:
            code_lines.append(raw_line)
            continue
            
        # Handle Markdown Tables
        if stripped.startswith('|') and stripped.endswith('|'):
            if '---' in stripped:
                continue
            table_lines.append(stripped)
            in_table = True
            continue
        elif in_table:
            if table_lines:
                rows_data = []
                for tline in table_lines:
                    cols = [c.strip() for c in tline.split('|')[1:-1]]
                    if cols:
                        rows_data.append(cols)
                if rows_data:
                    num_rows = len(rows_data)
                    num_cols = max(len(r) for r in rows_data)
                    tbl = doc.add_table(rows=num_rows, cols=num_cols)
                    tbl.alignment = WD_TABLE_ALIGNMENT.CENTER
                    for r_idx, row in enumerate(rows_data):
                        for c_idx, val in enumerate(row):
                            if c_idx < num_cols:
                                cell = tbl.cell(r_idx, c_idx)
                                cell.text = val
                                p = cell.paragraphs[0]
                                p.alignment = WD_ALIGN_PARAGRAPH.LEFT
                                if r_idx == 0:
                                    set_cell_background(cell, '0F172A')
                                    for run in p.runs:
                                        run.font.bold = True
                                        run.font.color.rgb = RGBColor(251, 191, 36)
                                else:
                                    if r_idx % 2 == 1:
                                        set_cell_background(cell, 'F8FAFC')
            table_lines = []
            in_table = False
            
        if not stripped:
            continue
            
        if stripped == '---':
            doc.add_page_break()
            continue
            
        # Headings
        if stripped.startswith('# '):
            h = doc.add_heading(level=1)
            h.alignment = WD_ALIGN_PARAGRAPH.CENTER
            run = h.add_run(stripped[2:])
            run.font.name = 'Times New Roman'
            run.font.size = Pt(18)
            run.font.bold = True
            run.font.color.rgb = RGBColor(15, 23, 42)
            h.paragraph_format.space_before = Pt(14)
            h.paragraph_format.space_after = Pt(12)
        elif stripped.startswith('## '):
            h = doc.add_heading(level=2)
            run = h.add_run(stripped[3:])
            run.font.name = 'Times New Roman'
            run.font.size = Pt(14)
            run.font.bold = True
            run.font.color.rgb = RGBColor(30, 41, 59)
            h.paragraph_format.space_before = Pt(12)
            h.paragraph_format.space_after = Pt(6)
        elif stripped.startswith('### '):
            h = doc.add_heading(level=3)
            run = h.add_run(stripped[4:])
            run.font.name = 'Times New Roman'
            run.font.size = Pt(12)
            run.font.bold = True
            run.font.color.rgb = RGBColor(180, 83, 9)
            h.paragraph_format.space_before = Pt(10)
            h.paragraph_format.space_after = Pt(4)
        elif stripped.startswith('#### '):
            h = doc.add_heading(level=4)
            run = h.add_run(stripped[5:])
            run.font.name = 'Times New Roman'
            run.font.size = Pt(11)
            run.font.bold = True
            run.font.color.rgb = RGBColor(15, 23, 42)
        elif stripped.startswith('* ') or stripped.startswith('- '):
            p = doc.add_paragraph(style='List Bullet')
            p.paragraph_format.space_after = Pt(4)
            text = stripped[2:]
            parts = text.split('**')
            for idx, part in enumerate(parts):
                if not part: continue
                run = p.add_run(part)
                run.font.name = 'Times New Roman'
                run.font.size = Pt(11)
                if idx % 2 == 1:
                    run.font.bold = True
        else:
            p = doc.add_paragraph()
            p.paragraph_format.space_after = Pt(6)
            p.paragraph_format.line_spacing = 1.15
            parts = stripped.split('**')
            for idx, part in enumerate(parts):
                if not part: continue
                run = p.add_run(part)
                run.font.name = 'Times New Roman'
                run.font.size = Pt(11)
                if idx % 2 == 1:
                    run.font.bold = True

    try:
        doc.save('KuralVerse_Internship_Report.docx')
        print('Successfully generated KuralVerse_Internship_Report.docx')
    except Exception as e:
        print('Error saving KuralVerse_Internship_Report.docx:', e)

    try:
        doc.save('KuralVerse_Project_Report.docx')
        print('Successfully saved KuralVerse_Project_Report.docx')
    except Exception as e:
        print('Note: KuralVerse_Project_Report.docx is currently open in Word.')

if __name__ == '__main__':
    create_word_report()
